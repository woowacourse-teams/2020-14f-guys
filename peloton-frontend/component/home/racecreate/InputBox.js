import React from "react";
import { StyleSheet, TextInput, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";

const InputBox = ({
  value,
  onChangeText,
  onClick,
  editable = true,
  number = false,
}) => {
  return (
    <TouchableOpacity
      style={styles.container}
      onPress={onClick}
      activeOpacity={1}
    >
      <TextInput
        style={styles.textBox}
        multiline
        textAlignVertical="top"
        value={value}
        onChangeText={onChangeText}
        editable={editable}
        keyboardType={number ? "number-pad" : "default"}
        pointerEvents={onClick ? "none" : "auto"}
      />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderStyle: "solid",
    borderBottomWidth: 1,
    borderColor: COLOR.WHITE3,
    paddingBottom: 6,
  },
  textBox: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
  },
});

export default InputBox;
