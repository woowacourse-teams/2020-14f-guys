import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import AsyncStorage from "@react-native-community/async-storage";
import { navigateWithoutHistory } from "../../../utils/util";
import { useNavigation } from "@react-navigation/core";
import { COLOR, TOKEN_STORAGE } from "../../../utils/constants";

const SignOutButtonContainer = () => {
  const navigation = useNavigation();
  const onSignOut = async () => {
    await AsyncStorage.removeItem(TOKEN_STORAGE);
    navigateWithoutHistory(navigation, "Login");
  };

  return (
    <View style={styles.signOutBtnContainer}>
      <TouchableOpacity style={styles.button} onPress={onSignOut}>
        <Text style={styles.buttonText}>Sign Out</Text>
      </TouchableOpacity>
      <Text style={styles.version}>Version 4.8.42</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  signOutBtnContainer: {
    flex: 1,
    alignItems: "center",
    paddingBottom: 600,
  },
  button: {
    marginTop: 50,
    borderWidth: 1,
    width: 150,
    height: 50,
    borderColor: COLOR.WHITE3,
    borderRadius: 100,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    color: COLOR.GREEN3,
    fontSize: 14,
  },
  version: {
    paddingTop: 15,
    color: COLOR.GREEN3,
    fontSize: 12,
  },
});

export default SignOutButtonContainer;
