import React from "react";
import { StyleSheet, TextInput } from "react-native";

const NicknameInput = ({ userInput, setUserInput, onSubmit }) => {
  return (
    <TextInput
      style={styles.nicknameInput}
      placeholder="닉네임을 입력해주세요"
      placeholderTextColor="gray"
      onChangeText={(text) => setUserInput(text)}
      onSubmitEditing={onSubmit}
      clearTextOnFocus={false}
      allowFontScaling={false}
      value={userInput}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  nicknameInput: {
    width: 300,
    height: 40,
    borderBottomWidth: 1,
    borderColor: "#4f83dd",
    fontSize: 17,
    color: "white",
  },
});

export default NicknameInput;
