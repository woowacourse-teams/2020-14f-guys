import React from "react";
import { Image, StyleSheet, TouchableWithoutFeedback } from "react-native";
import { useNavigation } from "@react-navigation/core";
import { logNav } from "../../../utils/Analytics";

const RaceCertificationImage = ({ certification, touchable, name, params }) => {
  const navigation = useNavigation();

  const navigateToScreen = (screen, param) => {
    logNav("Home", "CertificationDetail");
    navigation.navigate(screen, param);
  };

  return (
    <TouchableWithoutFeedback
      disabled={!touchable}
      onPress={() => navigateToScreen(name, params)}
    >
      <Image
        style={styles.image}
        source={
          certification
            ? { uri: certification.image }
            : require("../../../assets/empty-image.jpeg")
        }
      />
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  image: {
    width: "100%",
    height: "100%",
    resizeMode: "cover",
  },
  defaultImage: {
    width: "100%",
    height: "100%",
    resizeMode: "cover",
  },
  imageContainer: {
    alignItems: "center",
  },
});

export default RaceCertificationImage;
