import React from "react";
import { StyleSheet, TextInput } from "react-native";
import { COLOR } from "../../../utils/constants";

const InputBox = ({ value, onChangeText, editable = true, number = false }) => {
  return (
    <TextInput
      style={styles.container}
      multiline
      textAlignVertical="top"
      value={value}
      onChangeText={onChangeText}
      editable={editable}
      keyboardType={number ? "number-pad" : "default"}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: COLOR.WHITE,
    fontSize: 15,
    color: COLOR.GRAY1,
    fontWeight: "300",
    paddingHorizontal: 20,
    paddingTop: 10,
    paddingBottom: 10,
    textAlignVertical: "top",
    lineHeight: 20,
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 1,
  },
});

export default InputBox;
