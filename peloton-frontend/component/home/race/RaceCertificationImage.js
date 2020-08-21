import React from "react";
import { Image, StyleSheet, TouchableWithoutFeedback } from "react-native";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { useNavigation } from "@react-navigation/core";

const RaceCertificationImage = ({ item, touchable }) => {
  const navigation = useNavigation();

  const navigateToCertificationDetail = () => {
    navigation.navigate("CertificationDetail", {
      id: item.id,
    });
  };

  const navigateToImageDetail = () => {
    navigation.navigate("ImageDetail", {
      uri: item.image,
    });
  };

  return (
    <TouchableWithoutFeedback
      onPress={
        touchable ? navigateToCertificationDetail : navigateToImageDetail
      }
    >
      <Image
        style={styles.image}
        source={
          item
            ? { uri: item.image }
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
