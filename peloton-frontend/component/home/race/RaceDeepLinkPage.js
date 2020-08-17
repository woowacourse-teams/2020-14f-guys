import React, { useEffect } from "react";
import { StyleSheet, View } from "react-native";
import { useRecoilState, useSetRecoilState } from "recoil";
import AsyncStorage from "@react-native-community/async-storage";
import { useNavigation } from "@react-navigation/core";

import { loadingState } from "../../../state/loading/LoadingState";
import {
  COLOR,
  DEEP_LINK_BASE_URL,
  TOKEN_STORAGE,
} from "../../../utils/constants";
import {
  alertNotEnoughCash,
  navigateTabScreen,
  navigateWithHistory,
  navigateWithoutHistory,
} from "../../../utils/util";
import { MemberApi } from "../../../utils/api/MemberApi";
import { RaceApi } from "../../../utils/api/RaceApi";
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

const RedirectPage = ({ route }) => {
  const setLoadingState = useSetRecoilState(loadingState);
  const [token, setToken] = useRecoilState(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const navigation = useNavigation();

  const navigateToRaceDetail = () => {
    navigateWithHistory(navigation, [
      {
        name: "Home",
      },
      {
        name: "RaceDetail",
        params: {
          id: raceInfo.id,
        },
      },
    ]);
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
    const userCash = Number(memberInfo.cash);
    const raceEntranceFee = Number(raceInfo.entrance_fee);
    try {
      await RiderApi.post(token, raceInfo.id);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      navigateToRaceDetail();
    } catch (error) {
      console.log(error);
    }
    setLoadingState(false);
  };

  const isPayment = () => {
    const userCash = Number(memberInfo.cash);
    const raceEntranceFee = Number(raceInfo.entrance_fee);
    return userCash >= raceEntranceFee;
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
          navigateToRaceDetail();
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

export default RedirectPage;
