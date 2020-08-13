import React from "react";
import { StyleSheet, TouchableOpacity, View } from "react-native";
import { MaterialIcons } from "@expo/vector-icons";
import { COLOR } from "../../utils/constants";

const SubmitButton = ({
                        onSubmit,
                        color = COLOR.BLUE4,
                        arrowColor = COLOR.ARROW_BLUE,
                      }) => {
  return (
    <TouchableOpacity onPress={onSubmit}>
      <View
        style={{
          ...styles.nextButtonContainer,
          backgroundColor: color,
        }}
      >
        <MaterialIcons name="navigate-next" size={36} color={arrowColor} />
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  nextButtonContainer: {
    marginTop: 20,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE4,
    borderRadius: 40,
    width: 50,
    height: 50,
  },
});

export default SubmitButton;
