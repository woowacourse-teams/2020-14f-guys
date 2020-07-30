import React from "react";
import { StyleSheet, TextInput } from "react-native";

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
    flex: 6,
    borderStyle: "solid",
    borderBottomWidth: 1,
    borderColor: "#d5d5d5",
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    letterSpacing: 0,
    textAlign: "left",
    color: "#c3c3c3",
    paddingBottom: 6,
  },
});

export default InputBox;
