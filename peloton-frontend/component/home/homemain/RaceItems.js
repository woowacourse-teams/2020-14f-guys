import React from "react";
import { Dimensions, StyleSheet, View } from "react-native";
import Carousel from "react-native-snap-carousel";
import RaceItem from "./RaceItem";
import { SAMPLE_IMAGES } from "../../../utils/constants";

const RaceItems = ({ races, hasRace }) => {
  const _renderItems = ({ item }, parallaxProps) => {
    return (
      <RaceItem item={item} parallaxProps={parallaxProps} hasRace={hasRace} />
    );
  };

  const width = Math.round(Dimensions.get("window").width);

  const raceData = races.map((race) => {
    return {
      id: race.id,
      title: race.title,
      src: race.thumbnail,
      text: race.description,
    };
  });

  return (
    <View style={styles.container}>
      <Carousel
        data={hasRace ? raceData : SAMPLE_IMAGES}
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
