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
    <View style={styles.certificationContainer}>
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
    width: 200,
    height: 200,
    borderRadius: 10,
    overflow: "hidden",
  },
  imageContainer: {
    width: "100%",
    height: "100%",
  },
  image: {
    ...StyleSheet.absoluteFillObject,
    resizeMode: "cover",
  },
  certificationContainer: {
    height: 300,
    paddingHorizontal: 30,
    alignItems: "center",
  },
});

export default RaceCertificationImage;
