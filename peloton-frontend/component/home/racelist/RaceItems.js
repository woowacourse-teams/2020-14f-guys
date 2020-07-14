import React from "react";
import { Dimensions, StyleSheet, View } from "react-native";
import Carousel from "react-native-snap-carousel";
import RaceItem from "./RaceItem";
import { SAMPLE_IMAGES } from "../../../utils/constants";

const RaceItems = () => {
  const _renderItems = ({ item }, parallaxProps) => {
    return <RaceItem item={item} parallaxProps={parallaxProps} />;
  };

  const width = Math.round(Dimensions.get("window").width);

  return (
    <View style={styles.container}>
      <Carousel
        data={SAMPLE_IMAGES}
        sliderWidth={width}
        sliderHeight={180}
        itemWidth={250}
        renderItem={_renderItems}
        hasParallaxImages={true}
        loop={true}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    height: 300,
  },
});

export default RaceItems;
