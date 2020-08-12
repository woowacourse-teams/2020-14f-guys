import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import DateTimePickerModal from "react-native-modal-datetime-picker";

import { raceCreateInfoState } from "../../../state/race/RaceState";
import InputBox from "./InputBox";
import CalendarButton from "./CalendarButton";
import { DateFormatter } from "../../../utils/DateFormatter";
import { COLOR, RaceCreatUnitType } from "../../../utils/constants";
import MissionDaysSelector from "./MissionDaysSelector";

const RaceCreateUnit = ({
  type = RaceCreatUnitType.TEXT,
  number = false,
  fieldName,
  children,
}) => {
  // eslint-disable-next-line prettier/prettier
  const [raceCreateInfo, setRaceCreateInfo] = useRecoilState(raceCreateInfoState);
  const [isShowPicker, setIsShowPicker] = useState(false);

  const onPickDate = (pickedDate) => {
    let formattedDate = DateFormatter.yyyyMMdd(pickedDate);
    const currentDate = DateFormatter.yyyyMMdd(new Date());

    if (formattedDate < currentDate) {
      alert("현재보다 이전 날짜를 선택할 수 없습니다!");
      formattedDate = currentDate;
    }

    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: formattedDate,
    }));
    setIsShowPicker(false);
  };

  const onPickTime = (pickedTime) => {
    const hours = pickedTime.getHours();
    const minutes = pickedTime.getMinutes();

    if (hours < 0 || hours > 24) {
      alert("시간은 0~24 사이의 숫자여야 합니다.");
      return;
    }

    if (minutes < 0 || minutes > 60) {
      alert("분은 0~60 사이의 숫자여야 합니다.");
      return;
    }

    const timeFormat =
      (hours < 10 ? `0${hours}` : hours) +
      ":" +
      (minutes < 10 ? `0${minutes}` : minutes) +
      ":00";

    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: timeFormat,
    }));
    setIsShowPicker(false);
  };

  const onChangeText = (text) => {
    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: text,
    }));
  };

  const inputValue = () => {
    const value = raceCreateInfo[fieldName];

    return type === RaceCreatUnitType.TIME
      ? value && value.substring(0, 2) + "시 " + value.substring(3, 5) + "분"
      : value;
  };

  return (
    <View style={styles.container}>
      <View style={styles.subjectContainer}>
        <Text style={styles.subject}>{children}</Text>
      </View>
      {type === RaceCreatUnitType.DAYS ? (
        <MissionDaysSelector />
      ) : (
        <View style={styles.inputContainer}>
          <InputBox
            value={inputValue()}
            onChangeText={onChangeText}
            editable={type === RaceCreatUnitType.TEXT}
            number={number}
          />
          {type !== RaceCreatUnitType.TEXT && (
            <CalendarButton showCalendar={() => setIsShowPicker(true)} />
          )}
        </View>
      )}
      {isShowPicker && (
        <DateTimePickerModal
          isVisible={isShowPicker}
          testID="dateTimePicker"
          mode={type}
          onCancel={() => setIsShowPicker(false)}
          onConfirm={type === RaceCreatUnitType.DATE ? onPickDate : onPickTime}
          minimumDate={type === RaceCreatUnitType.DATE ? new Date() : null}
          locale="ko-KR"
          confirmTextIOS="확인"
          cancelTextIOS="취소"
          headerTextIOS="날짜를 골라주세요"
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: 56,
  },
  subjectContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 30,
  },
  subject: {
    fontSize: 18,
    fontWeight: "normal",
    lineHeight: 25,
    textAlign: "left",
    color: COLOR.GRAY1,
  },
  inputContainer: {
    flexDirection: "row",
  },
});

export default RaceCreateUnit;
