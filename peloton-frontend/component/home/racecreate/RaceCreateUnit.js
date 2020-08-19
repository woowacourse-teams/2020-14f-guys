import React, { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import DateTimePickerModal from "react-native-modal-datetime-picker";

import { raceCreateInfoState } from "../../../state/race/RaceState";
import InputBox from "./InputBox";
import { DateFormatter } from "../../../utils/DateFormatter";
import { COLOR, RaceCreateUnitType } from "../../../utils/constants";
import MissionDaysSelector from "./MissionDaysSelector";

const RaceCreateUnit = ({
  type = RaceCreateUnitType.TEXT,
  number = false,
  fieldName,
  children,
  postfix,
}) => {
  // eslint-disable-next-line prettier/prettier
  const [raceCreateInfo, setRaceCreateInfo] = useRecoilState(raceCreateInfoState);
  const [isShowPicker, setIsShowPicker] = useState(false);

  const convertISOFormatDate = (dateTime) => {
    return DateFormatter.yyyyMMdd(new Date(dateTime.toISOString()));
  };

  const onPickDate = (pickedDateTime) => {
    let formattedPickedDate = convertISOFormatDate(pickedDateTime);
    const currentFormattedSystemDate = convertISOFormatDate(new Date());

    if (formattedPickedDate < currentFormattedSystemDate) {
      alert("현재보다 이전 날짜를 선택할 수 없습니다!");
      formattedPickedDate = currentFormattedSystemDate;
    }

    setRaceCreateInfo((info) => ({
      ...info,
      [fieldName]: formattedPickedDate,
    }));
    setIsShowPicker(false);
  };

  const onPickTime = (pickedDate) => {
    let hours = pickedDate.getUTCHours();
    const minutes = pickedDate.getUTCMinutes();

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

  const convertTimeInput = (value) => {
    const timezoneOffsetHours = parseInt(new Date().getTimezoneOffset() / 60);
    const timezoneOffsetMinutes = new Date().getTimezoneOffset() % 60;
    let hours = value.split(":")[0] - timezoneOffsetHours;
    let minutes = value.split(":")[1] - timezoneOffsetMinutes;
    if (minutes > 60) {
      hours++;
      minutes -= 60;
    }
    if (minutes < 0) {
      hours--;
      minutes += 60;
    }
    if (hours >= 24) {
      hours -= 24;
    }
    if (hours < 0) {
      hours += 24;
    }
    hours = hours < 12 ? `0${hours}` : hours;
    minutes = minutes < 10 ? `0${minutes}` : minutes;
    return value && `${hours}시 ${minutes}분`;
  };

  const convertDateInput = (value) => {
    const currentFormattedSystemDate = convertISOFormatDate(new Date());
    const currentDate = DateFormatter.yyyyMMdd(new Date());
    if (currentFormattedSystemDate !== currentDate) {
      const diff =
        parseInt(currentDate.substring(8)) -
        parseInt(currentFormattedSystemDate.substring(8));

      return (
        value &&
        `${value.substring(0, 8) + (parseInt(value.substring(8)) + diff)}`
      );
    }
    return value;
  };

  const inputValue = () => {
    const value = raceCreateInfo[fieldName];

    if (type === RaceCreateUnitType.TIME) {
      return convertTimeInput(value);
    }
    if (type === RaceCreateUnitType.DATE) {
      return convertDateInput(value);
    }
    return value;
  };

  return (
    <View style={styles.container}>
      <View style={styles.subjectContainer}>
        <Text style={styles.subject}>{children}</Text>
      </View>
      {type === RaceCreateUnitType.DAYS ? (
        <MissionDaysSelector />
      ) : (
        <View style={styles.inputContainer}>
          <InputBox
            value={inputValue()}
            onChangeText={onChangeText}
            editable={type === RaceCreateUnitType.TEXT}
            number={number}
            onClick={
              type !== RaceCreateUnitType.TEXT
                ? () => setIsShowPicker(true)
                : null
            }
          />
          {postfix && <Text style={styles.postfix}>{postfix}</Text>}
        </View>
      )}
      {isShowPicker && (
        <DateTimePickerModal
          isVisible={isShowPicker}
          testID="dateTimePicker"
          mode={type}
          onCancel={() => setIsShowPicker(false)}
          onConfirm={type === RaceCreateUnitType.DATE ? onPickDate : onPickTime}
          minimumDate={type === RaceCreateUnitType.DATE ? new Date() : null}
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
  postfix: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
  },
});

export default RaceCreateUnit;
