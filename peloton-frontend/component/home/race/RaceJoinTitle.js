import React from "react";
import { Dimensions, Image, StyleSheet, Text, View } from "react-native";
import DefaultImage from "../../../assets/default-race-join.png";
import { COLOR } from "../../../utils/constants";

const RaceJoinTitle = ({ thumbnail, title }) => {
  return (
    <View style={styles.raceTitleContainer}>
      <Image
        source={{
          uri: thumbnail ? thumbnail : "../../../assets/default-race-join.png",
        }}
        defaultSource={DefaultImage}
        style={styles.thumbnail}
        blurRadius={1}
      />
      <Text style={styles.raceTitle}>{title}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  raceTitleContainer: {
    width: Dimensions.get("window").width,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE1,
  },
  raceTitle: {
    position: "absolute",
    fontSize: 20,
    color: COLOR.BLACK2,
    fontWeight: "600",
    lineHeight: 35,
  },
  thumbnail: {
    resizeMode: "stretch",
    width: 600,
    height: 200,
  },
});

export default RaceJoinTitle;
