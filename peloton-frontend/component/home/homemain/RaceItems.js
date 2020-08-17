import React from "react";
import { Dimensions, StyleSheet, View } from "react-native";
import Carousel from "react-native-snap-carousel";
import RaceItem from "./RaceItem";
import { useRecoilValue } from "recoil";
import { raceResponseState } from "../../../state/race/ResponseState";

const RaceItems = () => {
  const myRaces = useRecoilValue(raceResponseState);

  const _renderItems = ({ item }, parallaxProps) => {
    return <RaceItem item={item} parallaxProps={parallaxProps} />;
  };

  const width = Math.round(Dimensions.get("window").width);

  return (
    <View style={styles.container}>
      <Carousel
        data={myRaces}
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
