import React from "react";
import { Image, StyleSheet, View } from "react-native";
import Carousel from "react-native-snap-carousel";
import { MOCK_DATA } from "../../Const";
import RaceSubTitle from "./RaceSubTitle";

const render = ({ item }) => {
  return (
    <View style={styles.certificationImageContainer}>
      <Image style={styles.certificationImage} source={{ uri: item.url }} />
    </View>
  );
};

const RaceCertificationImage = () => {
  return (
    <View style={styles.certificationContainer}>
      <RaceSubTitle>인증 사진들</RaceSubTitle>
      <Carousel
        data={MOCK_DATA}
        renderItem={render}
        sliderWidth={600}
        itemWidth={200}
        loop={true}
        layoutCardOffset={20}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  certificationImageContainer: {
    width: 200,
    height: 200,
    borderRadius: 10,
    overflow: "hidden",
  },
  certificationImage: {
    flex: 1,
  },
  certificationContainer: {
    height: 300,
    paddingHorizontal: 30,
    alignItems: "center",
  },
});

export default RaceCertificationImage;
