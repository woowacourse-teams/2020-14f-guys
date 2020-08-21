import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilValue } from "recoil/dist";
import { raceInfoState } from "../../../state/race/RaceState";

const CalculationResult = ({ calculation, key }) => {
  const raceInfo = useRecoilValue(raceInfoState);

  const successRatio = (calculation.prize / raceInfo.entrance_fee) * 100;

  useEffect(() => {
    console.log(calculation.prize);
    console.log(raceInfo.entrance_fee);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.top}>
        <Text style={styles.riderName}>{calculation.riderId}</Text>
        <Text style={styles.prize}>{`${calculation.prize}원`}</Text>
      </View>
      <View style={styles.bottom}>
        <View style={[styles.successRatioBar, { width: `${successRatio}%` }]} />
        <Text style={styles.successRatioText}>{`${successRatio}%`}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    minHeight: 50,
    maxHeight: 80,
    marginBottom: 40,
  },
  top: {
    flex: 1,
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 5,
    // backgroundColor: COLOR.RED,
  },
  riderName: {
    flex: 1,
    minWidth: 50,
    fontSize: 18,
    fontWeight: "600",
    // backgroundColor: COLOR.GREEN1,
  },
  prize: {
    flex: 3,
    fontSize: 18,
    textAlign: "right",
    color: COLOR.DARK_GRAY6,
  },
  bottom: {
    flex: 1,
    flexDirection: "row",
    alignItems: "center",
    // backgroundColor: COLOR.BLACK,
  },
  successRatioBar: {
    height: "80%",
    fontSize: 20,
    backgroundColor: COLOR.PURPLE,
    borderRadius: 120,
  },
  successRatioText: {
    fontSize: 14,
    fontWeight: "400",
    color: COLOR.DARK_GRAY6,
    marginLeft: 10,
  },
});

export default CalculationResult;
