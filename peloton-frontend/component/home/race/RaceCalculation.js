import React, { useEffect, useState } from "react";
import { Alert, ScrollView, StyleSheet, Text, View } from "react-native";
import { COLOR, deviceHeight } from "../../../utils/constants";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil/dist";
import { useNavigation } from "@react-navigation/core";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { CalculationApi } from "../../../utils/api/CalculationApi";
import { raceAchievementState } from "../../../state/certification/RaceAchievementState";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { QueryApi } from "../../../utils/api/QueryApi";
import CalculationResults from "./CalculationResults";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const RaceCalculation = ({ route }) => {
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const memberInfo = useRecoilValue(memberInfoState);
  const ridersInfo = useRecoilValue(ridersInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const { id: raceId, calculations } = route.params;

  const [raceAchievement, setRaceAchievement] = useRecoilState(
    raceAchievementState
  );

  const findPrizeByMember = (memberId) => {
    const rider = ridersInfo.filter((rider) => rider.member_id === memberId)[0];
    const result = calculations.filter(
      (calculation) => calculation.rider_id === rider.id
    );
    if (result.length === 1) {
      return result[0].prize;
    }
    return 0;
  };

  useEffect(() => {
    setIsLoading(true);
    const fetchCalculations = async () => {
      try {
        const {
          race_achievement_rates: rates,
        } = await QueryApi.getRaceAchievement(token, raceId);
        const filteredRates = rates.filter((rate) => rate.member_id !== 0);
        const result = filteredRates.map((rate) => {
          const prize = findPrizeByMember(rate.member_id);
          return { ...rate, prize };
        });
        setRaceAchievement(result);
      } catch (e) {
        Alert.alert("", e.response.data.code);
        console.log(e.response.data.message);
        setIsLoading(false);
        navigation.goBack();
        return;
      }
      setIsLoading(false);
    };
    fetchCalculations();
  }, []);

  return (
    raceAchievement && (
      <LoadingIndicator>
        <ScrollView style={styles.container}>
          <View style={styles.top}>
            <Text style={styles.title}>{memberInfo.name}님,</Text>
            <Text style={styles.subtitle}>수고하셨습니다!</Text>
            <View style={styles.bannerSeparator} />
          </View>
          <View style={styles.bottom}>
            <CalculationResults achievementRates={raceAchievement} />
          </View>
        </ScrollView>
      </LoadingIndicator>
    )
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  top: {
    justifyContent: "center",
    paddingHorizontal: 35,
    backgroundColor: COLOR.LAVENDER,
    height: deviceHeight / 3,
  },
  title: {
    fontSize: 30,
    fontWeight: "600",
    color: COLOR.WHITE,
    marginBottom: 5,
  },
  subtitle: {
    fontSize: 24,
    fontWeight: "500",
    color: COLOR.WHITE,
    marginBottom: 19,
    lineHeight: 35,
  },
  bannerSeparator: {
    left: 0,
    borderBottomWidth: 1,
    width: 33,
    borderBottomColor: COLOR.WHITE,
  },
  bottom: {
    flex: 2,
  },
  loadingTextContainer: {
    alignItems: "center",
    justifyContent: "center",
    height: "100%",
  },
  loadingText: {
    color: COLOR.GRAY7,
  },
});

export default RaceCalculation;
