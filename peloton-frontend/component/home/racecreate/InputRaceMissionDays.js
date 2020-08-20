import React from "react";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue, useSetRecoilState } from "recoil";

import RaceCreateView from "./RaceCreateView";
import RaceCreateUnit from "./RaceCreateUnit";
import { DAYS, RaceCreateUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";

const InputRaceMissionDays = () => {
  const { start_date, end_date, mission_start_time, days } = useRecoilValue(
    raceCreateInfoState,
  );
  const navigation = useNavigation();
  const setDays = useSetRecoilState(raceCreateInfoState);

  const calculateDaysOffset = (time) => {
    const timezoneOffsetHours = parseInt(new Date().getTimezoneOffset() / 60);
    const timezoneOffsetMinutes = new Date().getTimezoneOffset() % 60;
    let hours = time.split(":")[0] - timezoneOffsetHours;
    let minutes = time.split(":")[1] - timezoneOffsetMinutes;
    if (minutes > 60) {
      hours++;
    }
    if (minutes < 0) {
      hours--;
    }

    let daysOffset = 0;
    if (hours < 0) {
      daysOffset--;
    }
    if (hours > 24) {
      daysOffset++;
    }
    return daysOffset;
  };

  const calculateDays = (daysOffset) => {
    if (daysOffset === 1) {
      return days.map(
        (day) => DAYS[DAYS.indexOf(day) === 6 ? 0 : DAYS.indexOf(day) + 1]
      );
    } else if (daysOffset === -1) {
      return days.map(
        (day) => DAYS[DAYS.indexOf(day) === 0 ? 6 : DAYS.indexOf(day) - 1]
      );
    }
    return days;
  };

  const convertUTCDays = () => {
    const startTimeDaysOffset = calculateDaysOffset(mission_start_time);

    return calculateDays(startTimeDaysOffset);
  };

  const navigateToMissionTime = async () => {
    const newDays = convertUTCDays();
    if (newDays.length === 0) {
      alert("요일을 하나 이상 선택해주세요");
      return;
    }
    setDays((prev) => ({
      ...prev,
      days: newDays,
    }));
    navigation.navigate("InputRaceFee");
  };

  return (
    <RaceCreateView onPress={navigateToMissionTime}>
      <RaceCreateUnit type={RaceCreateUnitType.DAYS} fieldName="days">
        미션이 진행될 요일을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionDays;
