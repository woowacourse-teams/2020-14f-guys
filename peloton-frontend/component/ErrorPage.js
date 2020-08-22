import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { navigateWithoutHistory } from "../utils/util";
import { useNavigation } from "@react-navigation/core";
import { COLOR } from "../utils/constants";

const ErrorPage = () => {
  const navigation = useNavigation();
  return (
    <View style={styles.container}>
      <Text style={styles.errorPageTitle}>죄송합니다..</Text>
      <TouchableOpacity
        onPress={() => navigateWithoutHistory(navigation, "Login")}
      >
        <View style={styles.goBackButton}>
          <Text style={styles.goBackButtonText}>되돌아가기</Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  errorPageTitle: {
    fontSize: 36,
    fontWeight: "100",
  },
  goBackButton: {
    marginTop: 250,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: COLOR.BLUE3,
    width: 200,
    height: 50,
  },
  goBackButtonText: {
    fontSize: 20,
    color: COLOR.WHITE,
    fontWeight: "200",
  },
});

export default ErrorPage;
