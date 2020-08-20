import React from "react";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue, useSetRecoilState } from "recoil";

import RaceCreateUnit from "./RaceCreateUnit";
import RaceCreateView from "./RaceCreateView";
import { DAYS, RaceCreateUnitType } from "../../../utils/constants";
import { raceCreateInfoState } from "../../../state/race/RaceState";

const InputRaceMissionTime = () => {
  const {
    start_date,
    end_date,
    mission_start_time,
    mission_end_time,
  } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const navigateToInputFee = () => {
    if (!mission_start_time || !mission_end_time) {
      alert("인증 시작, 마감 시간을 선택해주세요");
      return;
    }
    if (start_date === end_date && mission_start_time > mission_end_time) {
      alert("당일 미션은 인증 시작 시간이 마감 시간보다 빨라야합니다.");
      return;
    }

    const firstMissionDateTime = new Date(
      `${start_date}T${mission_start_time}Z`
    );
    const currentDateTime = new Date();
    if (firstMissionDateTime < currentDateTime) {
      alert("미션 시작 날짜와 시간은 현재시간 이후여야합니다.");
      return;
    }
    navigation.navigate("InputRaceMissionDays");
  };

  return (
    <RaceCreateView onPress={navigateToInputFee}>
      <RaceCreateUnit
        type={RaceCreateUnitType.TIME}
        fieldName="mission_start_time"
      >
        미션 시작 시간을 선택해주세요
      </RaceCreateUnit>
      <RaceCreateUnit
        type={RaceCreateUnitType.TIME}
        fieldName="mission_end_time"
      >
        미션 종료 시간을 선택해주세요
      </RaceCreateUnit>
    </RaceCreateView>
  );
};

export default InputRaceMissionTime;
