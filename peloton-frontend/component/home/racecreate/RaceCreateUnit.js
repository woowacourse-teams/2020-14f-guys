import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import DateTimePickerModal from "react-native-modal-datetime-picker";

import { raceCreateInfoState } from "../../../state/race/CreateState";
import InputBox from "./InputBox";
import CalendarButton from "./CalendarButton";
import { DateFormatter } from "../../../utils/DateFormatter";
import { COLOR } from "../../../utils/constants";

const RaceCreateUnit = ({
  date = false,
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

  const onChangeText = (text) => {
    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: text,
    }));
  };

  return (
    <View style={styles.container}>
      <View style={styles.subjectContainer}>
        <Text style={styles.subject}>{children}</Text>
      </View>
      <View style={styles.inputContainer}>
        <InputBox
          value={raceCreateInfo[fieldName]}
          onChangeText={onChangeText}
          editable={!date}
          number={number}
        />
        {date && <CalendarButton showCalendar={() => setIsShowPicker(true)} />}
      </View>
      {isShowPicker && (
        <DateTimePickerModal
          isVisible={isShowPicker}
          testID="dateTimePicker"
          value={date}
          mode="date"
          onCancel={() => setIsShowPicker(false)}
          onConfirm={onPickDate}
          minimumDate={new Date()}
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
