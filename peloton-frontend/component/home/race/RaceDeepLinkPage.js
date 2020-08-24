import React, { useEffect } from "react";
import { Alert, StyleSheet, View } from "react-native";
import { useRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import {
  COLOR,
  DEEP_LINK_BASE_URL,
  TOKEN_STORAGE,
} from "../../../utils/constants";
import AsyncStorage from "@react-native-community/async-storage";
import { useNavigation } from "@react-navigation/core";
import {
  alertNotEnoughCash,
  navigateTabScreen,
  navigateToRaceDetail,
  navigateWithoutHistory,
} from "../../../utils/util";
import { MemberApi } from "../../../utils/api/MemberApi";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { QueryApi } from "../../../utils/api/QueryApi";
import { RiderApi } from "../../../utils/api/RiderApi";
import FullWidthButton from "./FullWidthButton";
import RaceJoinTitle from "./RaceJoinTitle";
import RaceJoinBody from "./RaceJoinBody";

const RaceDeepLinkPage = ({ route }) => {
  const raceId = route.params.id;
  const setLoadingState = useSetRecoilState(loadingState);
  const [token, setToken] = useRecoilState(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const navigation = useNavigation();

  const isPayment = () => {
    const userCash = Number(memberInfo.cash);
    const raceEntranceFee = Number(raceInfo.entrance_fee);
    return userCash >= raceEntranceFee;
  };

  const chargeMoney = () => {
    alertNotEnoughCash({
      onOk: () => {
        navigateWithoutHistory(navigation, "Home");
        navigateTabScreen(navigation, "Profile");
      },
    });
  };

  const payEntranceFee = async () => {
    setLoadingState(true);
    try {
      await RiderApi.post(token, raceId);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      navigateToRaceDetail(navigation, raceId);
    } catch (error) {
      Alert.alert("", error.response.data.code);
      console.log(error.response.data.message);
    }
    setLoadingState(false);
  };

  useEffect(() => {
    setLoadingState(true);
    if (!raceId) {
      Alert.alert("", "정상적이지 않은 접근입니다.");
      navigateWithoutHistory(navigation, "Home");
      setLoadingState(false);
      return;
    }
    const fetchRaceInfo = async () => {
      let userToken = token;
      if (!token) {
        userToken = await AsyncStorage.getItem(TOKEN_STORAGE);
        if (!userToken) {
          Alert.alert("", "로그인 먼저 해주세요.");
          navigateWithoutHistory(navigation, "Login");
          setLoadingState(false);
          return;
        }
        setToken(userToken);
      }
      if (!userToken) {
        Alert.alert("", "로그인 먼저 해주세요.");
        navigateWithoutHistory(navigation, "Login");
        setLoadingState(false);
        return;
      }
      try {
        const newMemberInfo = await MemberApi.get(userToken);
        setMemberInfo(newMemberInfo);
      } catch (error) {
        Alert.alert("", error.response.data.code);
        console.log(error.response.data.message);
        navigateWithoutHistory(navigation, "Login");
        setLoadingState(false);
        return;
      }
      try {
        const newRaceInfo = await QueryApi.getRaceDetail(userToken, raceId);
        setRaceInfo(newRaceInfo);
      } catch (error) {
        Alert.alert("", error.response.data.code);
        console.log(error.response.data.message);
        navigateWithoutHistory(navigation, "Home");
        setLoadingState(false);
        return;
      }
      try {
        const { race_responses: races } = await QueryApi.getRaces(userToken);
        const filteredRaces = races.filter(
          (item) => String(item.id) === String(raceId)
        );
        if (filteredRaces.length > 0) {
          navigateToRaceDetail(navigation, raceId);
          return;
        }
      } catch (error) {
        Alert.alert("", error.response.data.code);
        console.log(error.response.data.message);
        navigateWithoutHistory(navigation, "Home");
      }
      setLoadingState(false);
    };
    fetchRaceInfo();
  }, []);

  return (
    <View style={styles.container}>
      <RaceJoinTitle thumbnail={raceInfo.thumbnail} title={raceInfo.title} />
      <RaceJoinBody raceInfo={raceInfo} memberInfo={memberInfo} />
      <FullWidthButton
        color={isPayment() ? COLOR.BLUE3 : COLOR.RED}
        onClick={isPayment() ? payEntranceFee : chargeMoney}
      >
        {isPayment() ? "결제하기" : "충전하기"}
      </FullWidthButton>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export const raceShareLink = (id) => {
  return `${DEEP_LINK_BASE_URL}home/races/${id}`;
};

export default RaceDeepLinkPage;
