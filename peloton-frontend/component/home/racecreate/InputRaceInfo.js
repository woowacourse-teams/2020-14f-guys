import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { useNavigation } from "@react-navigation/native";

import RaceCreateUnit from "./RaceCreateUnit";
import { useRecoilValue } from "recoil";
import { raceCreateInfoState } from "../../../state/race/CreateState";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const InputRaceInfo = () => {
  const navigation = useNavigation();
  const { title, description } = useRecoilValue(raceCreateInfoState);

  const onPress = async () => {
    if (!title || !description) {
      alert("필드를 모두 채워주세요");
      return;
    }
    navigation.navigate("InputRaceDates");
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <LoadingIndicator>
        <KeyboardAwareScrollView extraHeight={150} style={styles.container}>
          <View style={styles.subjectContainer}>
            <Text style={styles.subject}>{"Create" + "\n" + "Your Race"}</Text>
          </View>
          <RaceCreateUnit fieldName="title">
            Race의 이름을 선택해주세요
          </RaceCreateUnit>
          <RaceCreateUnit fieldName="description">
            레이스에 대해 설명해주세요
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
