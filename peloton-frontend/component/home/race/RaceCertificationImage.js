import React from "react";
import { StyleSheet, View } from "react-native";
import Carousel, { ParallaxImage } from "react-native-snap-carousel";
import { MOCK_DATA } from "../../Const";
import RaceSubTitle from "./RaceSubTitle";

const _renderEachImage = ({ item, index }, parallaxProps) => {
  return (
    <View style={styles.item}>
      <ParallaxImage
        containerStyle={styles.imageContainer}
        style={styles.image}
        source={{ uri: item.url }}
        {...parallaxProps}
      />
    </View>
  );
};

const RaceCertificationImage = () => {
  return (
    <View style={styles.container}>
      <RaceSubTitle>인증 사진들</RaceSubTitle>
      <Carousel
        data={MOCK_DATA}
        renderItem={_renderEachImage}
        sliderWidth={600}
        itemWidth={200}
        loop={true}
        hasParallaxImages={true}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  item: {
    width: 222,
    height: 222,
    justifyContent: "center",
    alignItems: "center",
    shadowColor: "#000",
    shadowOpacity: 0.3,
    shadowRadius: 6.68,
    elevation: 11,
    shadowOffset: {
      width: 2,
      height: 2,
    },
  },
  imageContainer: {
    width: "90%",
    height: "90%",
    borderRadius: 10,
    overflow: "hidden",
  },
  image: {
    ...StyleSheet.absoluteFillObject,
    resizeMode: "cover",
  },
  container: {
    marginTop: 20,
    height: 300,
    paddingHorizontal: 30,
    alignItems: "center",
  },
});

export default RaceCertificationImage;
