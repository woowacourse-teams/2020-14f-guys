import React from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
} from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";

const RaceCreateView = ({ children, onPress }) => {
  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <KeyboardAwareScrollView
        extraHeight={200}
        contentContainerStyle={styles.container}
      >
        {children}
        <TouchableOpacity style={styles.button} onPress={onPress}>
          <Text style={styles.buttonText}>NEXT</Text>
        </TouchableOpacity>
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
    marginTop: 100,
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    fontSize: 14,
  },
});

export default RaceCreateView;
