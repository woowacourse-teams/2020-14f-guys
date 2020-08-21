import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilValue } from "recoil/dist";
import { memberInfoState, memberTokenState, } from "../../../state/member/MemberState";
import { CalculationApi } from "../../../utils/api/CalculationApi";
import CalculationResults from "./CalculationResults";

const RaceCalculation = ({ route }) => {
  const token = useRecoilValue(memberTokenState);
  const memberInfo = useRecoilValue(memberInfoState);
  const raceId = route.params.id;

  const [isCalculated, setIsCalculated] = useState(false);
  const [calculations, setCalculations] = useState(null);

  useEffect(() => {
    const fetchCalculations = () => {
      CalculationApi.get(token, raceId)
        .then((calculations) => {
          setCalculations(calculations);
          setIsCalculated(true);
        })
        .catch((e) => alert(e.response.data.message));
    };
    // fetchCalculations();

    const mockCalculations = [
      {
        riderId: 1,
        raceId: 100,
        prize: 1000,
        calculated: false,
        createdAt: null,
      },
      {
        riderId: 2,
        raceId: 100,
        prize: 2000,
        calculated: false,
        createdAt: null,
      },
      {
        riderId: 3,
        raceId: 100,
        prize: 4000,
        calculated: false,
        createdAt: null,
      },
    ];
    setCalculations(mockCalculations);
    setIsCalculated(true);
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
          <CalculationResults calculations={calculations} />
        ) : (
          <Text>정산중</Text>
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
});

export default RaceCalculation;
