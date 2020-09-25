import React from "react";
import { StyleSheet, View } from "react-native";
import { useRecoilState } from "recoil";

import { COLOR, DAYS } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import MissionDayButton from "./MissionDayButton";

const MissionDaysSelector = () => {
  const [{ days }, setRaceCreateInfo] = useRecoilState(raceCreateInfoState);

  const toggleDay = (day) => {
    let updatedDays;
    const daysWithoutDay = days.filter((it) => it !== day);
    if (daysWithoutDay.length === days.length) {
      updatedDays = [...days, day];
    } else {
      updatedDays = daysWithoutDay;
    }
    setRaceCreateInfo((prev) => {
      return { ...prev, days: updatedDays };
    });
  };

  return (
    <View style={styles.container}>
      {DAYS.map((day) => (
        <MissionDayButton
          key={day}
          day={day}
          toggleDay={() => toggleDay(day)}
          clicked={days.filter((it) => it === day).length > 0}
        />
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    justifyContent: "center",
    flexWrap: "wrap",
    width: 300,
  },
  button: {
    backgroundColor: COLOR.GREEN1,
    borderRadius: 40,
    width: 55,
    height: 55,
    marginHorizontal: 10,
    marginBottom: 20,
    overflow: "hidden",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default MissionDaysSelector;
