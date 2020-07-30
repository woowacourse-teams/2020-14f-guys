import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import DateTimePickerModal from "react-native-modal-datetime-picker";

import { raceCreateInfoState } from "../../../state/race/CreateState";
import InputBox from "./InputBox";
import CalendarButton from "./CalendarButton";
import { DateFormatter } from "../../../utils/DateFormatter";

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
    const formattedDate = DateFormatter.yyyyMMdd(pickedDate);

    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: formattedDate,
    }));
    setIsShowPicker(false);
  };

  const onChangeText = (text) => {
    setRaceCreateInfo((info) => ({ ...info, [fieldName]: text }));
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
    letterSpacing: 0,
    textAlign: "left",
    color: "#a0a0a0",
  },
  inputContainer: {
    flexDirection: "row",
  },
});

export default RaceCreateUnit;
