import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import DateTimePickerModal from "react-native-modal-datetime-picker";

import { raceCreateInfoState } from "../../../state/race/CreateState";
import InputBox from "./InputBox";
import CalendarButton from "./CalendarButton";

const RaceCreateUnit = ({ date = false, fieldName, children }) => {
  const [raceCreateInfo, setRaceCreateInfo] = useRecoilState(
    raceCreateInfoState,
  );
  const [isShowPicker, setIsShowPicker] = useState(false);

  const onPickDate = (pickedDate) => {
    const formattedDate = pickedDate.toISOString().split("T")[0];

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
        {date && <CalendarButton showCalendar={() => setIsShowPicker(true)} />}
      </View>
      <InputBox
        value={raceCreateInfo[fieldName]}
        onChangeText={onChangeText}
        editable={!date}
      />
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
    marginBottom: 30,
  },
  subjectContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  subject: {
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default RaceCreateUnit;
