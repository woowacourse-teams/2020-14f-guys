import React from "react";
import { Image, StyleSheet, TouchableWithoutFeedback } from "react-native";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { useNavigation } from "@react-navigation/core";

const RaceCertificationImage = ({ touchable, item }) => {
  const navigation = useNavigation();

  const navigateToCertificationDetail = () => {
    navigation.navigate("CertificationDetail", {
      item,
    });
  };

  return (
    <LoadingIndicator>
      <TouchableWithoutFeedback
        onPress={() => navigateToCertificationDetail()}
        disabled={!touchable}
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
    </LoadingIndicator>
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
