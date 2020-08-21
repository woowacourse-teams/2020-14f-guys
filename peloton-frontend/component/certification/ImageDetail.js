import React from "react";
import { Text, View, StyleSheet, Image } from "react-native";
import { COLOR } from "../../utils/constants";

const ImageDetail = ({ route }) => {
  const uri = route.params.uri;

  return (
    <View style={styles.container}>
      <Image source={{ uri }} style={styles.imageDetail} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.BLACK,
  },
  imageDetail: {
    height: "100%",
    resizeMode: "contain",
  },
});

export default ImageDetail;
