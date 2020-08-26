import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilState, useRecoilValue } from "recoil/dist";
import { useNavigation } from "@react-navigation/core";
import { memberInfoState, memberTokenState, } from "../../../state/member/MemberState";
import { CalculationApi } from "../../../utils/api/CalculationApi";
import { raceAchievementState } from "../../../state/certification/RaceAchievementState";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { QueryApi } from "../../../utils/api/QueryApi";
import CalculationResults from "./CalculationResults";

const RaceCalculation = ({ route }) => {
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const memberInfo = useRecoilValue(memberInfoState);
  const ridersInfo = useRecoilValue(ridersInfoState);
  const [isCalculated, setIsCalculated] = useState(false);
  const raceId = route.params.id;

  const [raceAchievement, setRaceAchievement] = useRecoilState(
    raceAchievementState,
  );

  useEffect(() => {
    setIsCalculated(false);
    console.log("------");
    console.log("raceId");
    console.log(raceId);
    const fetchCalculations = async () => {
      try {
        await CalculationApi.post(token, raceId);
      } catch (e) {
        alert(e.response.data.message);
      }
      try {
        const achievement = await QueryApi.getRaceAchievement(token, raceId);

        const { calculationResponses: calculations } = await CalculationApi.get(
          token,
          raceId
        );
        console.log("--");
        console.log("achievement");
        console.log(achievement);
        console.log("---------");

        const findPrizeByMember = (memberId) => {
          const rider = ridersInfo.filter(
            (rider) => rider.member_id === memberId,
          )[0];
          return calculations.filter(
            (calculation) => calculation.rider_id === rider.id
          )[0].prize;
        };

        achievement.race_achievement_rates.map(
          (rate) => (rate.prize = findPrizeByMember(rate.member_id))
        );
        setRaceAchievement(achievement);
      } catch (e) {
        navigation.goBack();
        console.log(e.response.data.message);
      }
    };
    fetchCalculations().then(() => setIsCalculated(true));
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.top}>
        <Text style={styles.title}>{memberInfo.name}님,</Text>
        <Text style={styles.subtitle}>수고하셨습니다!</Text>
        <View style={styles.bannerSeparator} />
      </View>
      <View style={styles.bottom}>
        {isCalculated ? (
          <CalculationResults
            achievementRates={raceAchievement.race_achievement_rates}
          />
        ) : (
          <View style={styles.loadingTextContainer}>
            <Text style={styles.loadingText}>잠시만 기다려주세요!</Text>
          </View>
        )}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
  },
  top: {
    justifyContent: "center",
    paddingHorizontal: 35,
    backgroundColor: COLOR.LAVENDER,
    flex: 1,
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
