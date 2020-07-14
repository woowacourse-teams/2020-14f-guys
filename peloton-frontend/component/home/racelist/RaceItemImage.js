import React from "react";
import { StyleSheet } from "react-native";
import { ParallaxImage } from "react-native-snap-carousel";

const RaceItemImage = ({ item, parallaxProps }) => {
  return (
    <ParallaxImage
      source={{
        uri: item.src,
      }}
      style={styles.carouselImage}
      containerStyle={styles.container}
      parallaxFactor={0.1}
      {...parallaxProps}
    />
  );
};

export default RaceItemImage;

const styles = StyleSheet.create({
  container: {
    flex: 7,
    borderTopStartRadius: 20,
    borderTopEndRadius: 20,
    width: 250,
  },
  carouselImage: {
    ...StyleSheet.absoluteFillObject,
    flex: 4,
    resizeMode: "cover",
  },
});
