import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { COLOR } from "../../utils/constants";

const ProfileEditButton = ({ text, onPress }) => {
  return (
    <View style={styles.buttonContainer}>
      <TouchableOpacity style={styles.button} onPress={onPress}>
        <Text style={styles.buttonText}>{text}</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  buttonContainer: {
    marginTop: 10,
    height: 45,
  },
  button: {
    borderWidth: 1,
    padding: 10,
    borderColor: COLOR.GRAY5,
    borderRadius: 100,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    color: COLOR.WHITE,
    fontSize: 14,
  },
});

export default ProfileEditButton;
