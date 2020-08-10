import React from "react";
import { StyleSheet, TextInput } from "react-native";
import { COLOR } from "../../utils/constants";

const NicknameInput = ({ userInput, setUserInput, onSubmit }) => {
  return (
    <TextInput
      style={styles.nicknameInput}
      placeholder="닉네임을 입력해주세요"
      placeholderTextColor={COLOR.GRAY1}
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
    borderColor: COLOR.BLUE3,
    fontSize: 17,
    color: COLOR.WHITE,
  },
});

export default NicknameInput;
