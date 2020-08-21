import React from "react";
import { ActivityIndicator, StyleSheet, View } from "react-native";
import {
  useRecoilState,
  useRecoilValue,
  useResetRecoilState,
  useSetRecoilState,
} from "recoil";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/RaceState";
import { COLOR, DAYS } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import RaceCreateView from "./RaceCreateView";
import {
  alertNotEnoughCash,
  navigateTabScreen,
  navigateWithHistory,
} from "../../../utils/util";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { RaceApi } from "../../../utils/api/RaceApi";
import { MemberApi } from "../../../utils/api/MemberApi";
import { RiderApi } from "../../../utils/api/RiderApi";

const InputRaceFee = () => {
  // eslint-disable-next-line prettier/prettier
  const { title, description, start_date, end_date, category, entrance_fee, mission_start_time, mission_end_time } = useRecoilValue(
    raceCreateInfoState);
  const resetRaceCreateInfo = useResetRecoilState(raceCreateInfoState);
  const [loading, setGlobalLoading] = useRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [{ days }, setDaysInRaceCreateInfo] = useRecoilState(
    raceCreateInfoState,
  );

  const navigation = useNavigation();

  const formatPostRaceBody = () => {
    return {
      title,
      description,
      category,
      entrance_fee,
      race_duration: {
        start_date,
        end_date,
      },
      days,
      certification_available_duration: {
        start_time: mission_start_time,
        end_time: mission_end_time,
      },
    };
  };

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
      return days.map((day) => DAYS[(DAYS.indexOf(day) + 1) % 7]);
    } else if (daysOffset === -1) {
      return days.map(
        (day) => DAYS[DAYS.indexOf(day) === 0 ? 6 : DAYS.indexOf(day) - 1],
      );
    }
    return days;
  };

  const convertUTCDays = () => {
    const startTimeDaysOffset = calculateDaysOffset(mission_start_time);

    return calculateDays(startTimeDaysOffset);
  };

  const createRaceRequest = async () => {
    setGlobalLoading(true);
    try {
      const { location } = await RaceApi.post(token, formatPostRaceBody());
      const race_id = location.split("/")[3];
      await RiderApi.post(token, race_id);
      resetRaceCreateInfo();
      navigateWithHistory(navigation, [
        { name: "Home" },
        {
          name: "RaceDetail",
          params: { id: race_id },
        },
      ]);
    } catch (e) {
      alert(e.response.data.code);
    }
    setGlobalLoading(false);
  };

  const calculateAvailableDays = () => {
    const startDateTime = new Date(
      `${start_date}T${mission_start_time}Z`,
    ).getTime();
    const endDateTime = new Date(`${end_date}T${mission_end_time}Z`).getTime();

    const oneDayMillis = 1000 * 60 * 60 * 24;
    let availableDays = [];
    for (let i = startDateTime; i <= endDateTime; i += oneDayMillis) {
      const tempDay = new Date(i).getDay();
      if (availableDays.includes(DAYS[tempDay])) {
        continue;
      }
      availableDays.push(DAYS[tempDay]);
    }
    console.log(availableDays);
    return availableDays;
  };

  const submitRaceRequest = async () => {
    const userCash = Number(memberInfo.cash);

    if (!entrance_fee) {
      alert("입장료를 입력해주세요");
      return;
    }
    if (entrance_fee < 0) {
      alert("입장료는 음수가 될 수 없습니다");
      return;
    }
    if (userCash < entrance_fee) {
      alertNotEnoughCash({
        onOk: () => navigateTabScreen(navigation, "Profile"),
      });
      setGlobalLoading(false);
      return;
    }

    const availableDays = calculateAvailableDays();
    const newDays = convertUTCDays();
    const filteredDays = newDays.filter((day) => !availableDays.includes(day));
    if (filteredDays.length > 0) {
      alert("레이스 기간에 해당하지 않는 요일 선택이 존재합니다.");
      return;
    }

    setDaysInRaceCreateInfo((prev) => ({
      ...prev,
      days: newDays,
    }));

    try {
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      await createRaceRequest();
    } catch (e) {
      console.log(e.response.data.message);
    }
  };

  return (
    <LoadingIndicator>
      <RaceCreateView onPress={submitRaceRequest}>
        <RaceCreateUnit postfix="원" fieldName="entrance_fee" number>
          Race의 입장료를 결정해주세요
        </RaceCreateUnit>
      </RaceCreateView>
      {loading && (
        <View style={styles.loadingIndicator}>
          <ActivityIndicator size="large" color={COLOR.GRAY5} />
        </View>
      )}
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  subjectContainer: {
    marginBottom: 120,
  },
});

export default InputRaceFee;
