import React from "react";
import { Image, StyleSheet, TouchableOpacity, View } from "react-native";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { useNavigation } from "@react-navigation/core";
import { CommonActions } from "@react-navigation/native";

const RaceCertificationImage = ({ item }) => {
  const navigation = useNavigation();

  const navigateToCertificationDetail = async () => {
    navigation.dispatch(
      CommonActions.navigate({
        name: "CertificationDetail",
        params: {
          item,
        },
      })
    );
  };

  return item ? (
    <LoadingIndicator>
      <TouchableOpacity onPress={() => navigateToCertificationDetail()}>
        <Image
          style={styles.image}
          source={{ uri: item.image }}
          defaultSource={require("../../../assets/empty-image.jpeg")}
        />
      </TouchableOpacity>
    </LoadingIndicator>
  ) : (
    <View style={styles.imageContainer}>
      <Image
        style={styles.defaultImage}
        source={require("../../../assets/empty-image.jpeg")}
        defaultSource={require("../../../assets/empty-image.jpeg")}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  image: {
    height: 600,
    resizeMode: "cover",
  },
  defaultImage: {
    height: 600,
    resizeMode: "center",
  },
  imageContainer: {
    alignItems: "center",
  },
});

export default RaceCertificationImage;
