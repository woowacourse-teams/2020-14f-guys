import React from "react";
import { StyleSheet, View } from "react-native";
import Carousel, { ParallaxImage } from "react-native-snap-carousel";
import { MOCK_DATA } from "../../../utils/constants";

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
      <View style={styles.items}>
        <Carousel
          data={MOCK_DATA}
          renderItem={_renderEachImage}
          sliderWidth={420}
          itemWidth={420}
          loop={true}
          hasParallaxImages={true}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  items: {
    height: 600,
    alignItems: "center",
  },
  item: {
    width: "100%",
    height: 600,
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
    width: "100%",
    height: "100%",
    borderRadius: 10,
    overflow: "hidden",
  },
  image: {
    ...StyleSheet.absoluteFillObject,
    resizeMode: "cover",
  },
});

export default RaceCertificationImage;
