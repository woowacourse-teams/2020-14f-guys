import React from "react";
import { StyleSheet, TouchableOpacity } from "react-native";
import { COLOR } from "../../../utils/constants";
import { useNavigation } from "@react-navigation/core";
import { AntDesign, Entypo, MaterialIcons } from "@expo/vector-icons";

const GoBackButton = () => {
  const navigation = useNavigation();

  const goBack = async () => {
    if (navigation.canGoBack()) {
      navigation.goBack();
    }
  };

  return (
    <TouchableOpacity style={styles.goBackButton} onPress={goBack}>
      <Entypo name="chevron-thin-left" size={24} color="black" />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  goBackButton: {
    width: 50,
    height: 50,
    borderRadius: 30,
    backgroundColor: "rgba(255,255,255,0.2)",
    alignItems: "center",
    justifyContent: "center",
  },
});

export default GoBackButton;
