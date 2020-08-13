import React, { useEffect } from "react";
import { Alert, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { COLOR, TOKEN_STORAGE } from "../../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import {
  navigateTabScreen,
  navigateWithHistory,
  navigateWithoutHistory,
} from "../../../utils/util";
import { useNavigation } from "@react-navigation/core";
import { MemberApi } from "../../../utils/api/MemberApi";
import { RaceApi } from "../../../utils/api/RaceApi";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { QueryApi } from "../../../utils/api/QueryApi";
import { RiderApi } from "../../../utils/api/RiderApi";

const RedirectPage = ({ route }) => {
  const setLoadingState = useSetRecoilState(loadingState);
  const [token, setToken] = useRecoilState(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const navigation = useNavigation();

  const joinRace = async () => {
    setLoadingState(true);
    const userCash = Number(memberInfo.cash);
    const raceEntranceFee = Number(raceInfo.entrance_fee);
    if (userCash < raceEntranceFee) {
      Alert.alert(
        "잔액이 부족합니다.",
        "캐시 충전 페이지로 이동하시겠습니까?",
        [
          {
            text: "Cancel",
            style: "cancel",
          },
          {
            text: "OK",
            onPress: () => {
              navigateWithoutHistory(navigation, "Home");
              navigateTabScreen(navigation, "Profile");
            },
          },
        ],
        { cancelable: false },
      );
      setLoadingState(false);
      return;
    }
    try {
      await MemberApi.patchCash(token, String(userCash - raceEntranceFee));
      await RiderApi.post(token, raceInfo.id);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      navigateWithHistory(navigation, [
        {
          name: "Home",
        },
        {
          name: "RaceDetail",
          params: {
            raceInfo,
            location: `/api/races/${raceInfo.id}`,
          },
        },
      ]);
    } catch (error) {
      console.log(error);
    }
    setLoadingState(false);
  };

  useEffect(() => {
    setLoadingState(true);
    const fetchRaceInfo = async () => {
      let userToken = token;
      if (!token) {
        userToken = await AsyncStorage.getItem(TOKEN_STORAGE);
        setToken(userToken);
      }
      if (!route || !route.params || !route.params.id) {
        alert("정상적이지 않은 접근입니다.");
        navigateWithoutHistory(navigation, "Home");
        return;
      }
      const raceId = route.params.id;
      if (!userToken) {
        alert("로그인 먼저 해주세요.");
        navigateWithoutHistory(navigation, "Login");
        return;
      }
      try {
        const newMemberInfo = await MemberApi.get(userToken);
        setMemberInfo(newMemberInfo);
      } catch (error) {
        alert("사용자 정보를 찾을 수 없습니다. 다시 로그인 해주세요.");
        navigateWithoutHistory(navigation, "Login");
      }
      try {
        const newRaceInfo = await RaceApi.get(userToken, raceId);
        setRaceInfo(newRaceInfo);
      } catch (error) {
        alert("올바르지 않은 경로입니다.");
        navigateWithoutHistory(navigation, "Home");
      }
      try {
        const { race_responses: races } = await QueryApi.getRaces(userToken);
        const filteredRace = races.filter((race) => String(race.id) === raceId);
        if (filteredRace.length > 0) {
          navigateWithHistory(navigation, [
            {
              name: "Home",
            },
            {
              name: "RaceDetail",
              params: {
                raceInfo,
                location: `/api/races/${raceId}`,
              },
            },
          ]);
        }
      } catch (error) {
        alert("조회에 실패했습니다.");
        console.log(error);
        navigateWithoutHistory(navigation, "Home");
      }
    };
    fetchRaceInfo();
    setLoadingState(false);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.raceTitleContainer}>
        <Text style={styles.raceTitle}>레이스 제목 : {raceInfo.title}</Text>
      </View>
      <View style={styles.raceBody}>
        <Text style={styles.raceDescription}>
          레이스 설명 : {raceInfo.description}
        </Text>
        <Text style={styles.raceDescription}>잔액 : {memberInfo.cash}</Text>
        <Text style={styles.entranceFee}>
          {raceInfo.entrance_fee}원이 차감됩니다
        </Text>
      </View>
      <TouchableOpacity onPress={joinRace}>
        <View style={styles.paymentButton}>
          <Text style={styles.paymentText}>결제하기</Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  raceTitleContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE1,
  },
  raceTitle: {
    fontSize: 36,
    color: COLOR.WHITE,
    fontWeight: "200",
  },
  raceBody: {
    flex: 5,
  },
  raceDescription: {
    fontSize: 20,
  },
  entranceFee: {
    fontSize: 28,
    color: COLOR.LAVENDER,
  },
  paymentButton: {
    width: 150,
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 10,
    backgroundColor: COLOR.BLUE6,
  },
  paymentText: {
    color: COLOR.WHITE,
    fontSize: 15,
    fontWeight: "600",
  },
});

export default RedirectPage;
