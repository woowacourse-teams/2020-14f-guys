import React from "react";
import { StyleSheet, Text, TextInput, View } from "react-native";
import { useRecoilState } from "recoil";

import { raceCreateInfoState } from "../../../state/race/CreateState";

const RaceCreateInputBox = ({ fieldName, children }) => {
  const [raceCreateInfo, setRaceCreateInfo] = useRecoilState(
    raceCreateInfoState,
  );

  const onChangeText = (text) => {
    setRaceCreateInfo((info) => ({ ...info, [fieldName]: text }));
  };

  return (
    <View style={styles.container}>
      <Text>{children} : </Text>
      <TextInput
        style={styles.inputBox}
        value={raceCreateInfo[fieldName]}
        onChangeText={onChangeText}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 30,
  },
  inputBox: {
    width: 100,
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
  },
});

export default RaceCreateInputBox;
