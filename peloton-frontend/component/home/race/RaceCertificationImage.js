import React from "react";
import { Image, StyleSheet } from "react-native";

const RaceCertificationImage = ({ uri }) => {
  return (
    <Image
      style={styles.image}
      source={uri ? { uri } : require("../../../assets/default-race-join.png")}
    />
  );
};

const styles = StyleSheet.create({
  image: {
    height: 600,
    resizeMode: "cover",
  },
});

export default RaceCertificationImage;
