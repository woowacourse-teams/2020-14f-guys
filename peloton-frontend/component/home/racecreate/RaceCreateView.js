import React from "react";
import {
  Keyboard,
  StyleSheet,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import SubmitButton from "../../login/SubmitButton";
import { COLOR } from "../../../utils/constants";

const RaceCreateView = ({ children, onPress }) => {
  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <KeyboardAwareScrollView
        extraHeight={200}
        contentContainerStyle={styles.container}
      >
        {children}
        <View style={styles.button}>
          <SubmitButton
            onSubmit={onPress}
            color={COLOR.GRAY3}
            arrowColor={COLOR.WHITE}
          />
        </View>
      </KeyboardAwareScrollView>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    backgroundColor: "#FFFFFF",
    paddingBottom: 20,
    paddingHorizontal: 33,
  },
  button: {
    position: "absolute",
    bottom: 50,
    alignSelf: "center",
  },
  buttonText: {
    fontSize: 14,
  },
});

export default RaceCreateView;
