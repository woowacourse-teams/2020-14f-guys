import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const InputRaceInfo = () => {
  const { startDate, endDate } = useRecoilValue(raceCreateInfoState);
  const navigation = useNavigation();

  const onPress = async () => {
    if (!startDate || !endDate) {
      alert("필드를 모두 채워주세요");
      return;
    }
    if (startDate > endDate) {
      alert("레이스 종료 날짜가 시작 날짜보다 빠릅니다.");
      return;
    }
    navigation.navigate("InputRaceFee");
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <LoadingIndicator>
        <KeyboardAwareScrollView extraHeight={150} style={styles.container}>
          <View style={styles.subjectContainer}>
            <Text style={styles.subject}>{"Create" + "\n" + "Your Race"}</Text>
          </View>
          <RaceCreateUnit date fieldName="startDate">
            레이스가 시작되는 날짜를 입력해주세요
          </RaceCreateUnit>
          <RaceCreateUnit date fieldName="endDate">
            레이스가 종료되는 날짜를 입력해주세요
          </RaceCreateUnit>
          <TouchableOpacity style={styles.button} onPress={onPress}>
            <Text style={styles.buttonText}>NEXT</Text>
          </TouchableOpacity>
        </KeyboardAwareScrollView>
      </LoadingIndicator>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#FFFFFF",
    paddingTop: 67,
    paddingBottom: 20,
    paddingHorizontal: 33,
  },
  subjectContainer: {
    marginBottom: 50,
  },
  subject: {
    fontSize: 30,
    fontWeight: "bold",
    lineHeight: 35,
    letterSpacing: 0,
    textAlign: "left",
    color: "#1b1c20",
  },
  button: {
    width: 178,
    height: 50,
    borderRadius: 100,
    backgroundColor: "#ffffff",
    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 0.5,
    marginBottom: 50,
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    fontSize: 14,
  },
});

export default InputRaceInfo;
