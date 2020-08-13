import React from "react";
import { StyleSheet, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";
import { useNavigation } from "@react-navigation/core";
import { AntDesign } from "@expo/vector-icons";

const GoBackButton = () => {
  const navigation = useNavigation();

  const goBack = async () => {
    if (navigation.canGoBack()) {
      navigation.goBack();
    }
  };

  return (
    <TouchableOpacity style={styles.goBackButton} onPress={goBack}>
      <AntDesign name="left" size={30} color={COLOR.BLACK} />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  goBackButton: {
    padding: 10,
  },
});

export default GoBackButton;
