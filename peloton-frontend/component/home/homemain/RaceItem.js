import React from "react";
import { StyleSheet, TouchableOpacity, View } from "react-native";
import RaceItemImage from "./RaceItemImage";
import RaceItemText from "./RaceItemText";
import { COLOR } from "../../../utils/constants";
import { useNavigation } from "@react-navigation/native";
import { useRecoilValue } from "recoil";
import { raceResponseState } from "../../../state/race/ResponseState";

const RaceItem = ({ item, parallaxProps }) => {
  const navigation = useNavigation();
  const myRaces = useRecoilValue(raceResponseState);
  const onItemClick = () => {
    navigation.navigate({
      name: "RaceDetail",
      params: { location: `/api/races/${item.id}` },
    });
  };

  return myRaces.length !== 0 ? (
    <TouchableOpacity style={styles.container} onPress={onItemClick}>
      <RaceItemImage item={item} parallaxProps={parallaxProps} />
      <RaceItemText item={item} />
    </TouchableOpacity>
  ) : (
    <View style={styles.container}>
      <RaceItemImage item={item} parallaxProps={parallaxProps} />
      <RaceItemText item={item} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderRadius: 20,
    backgroundColor: COLOR.WHITE,
    marginVertical: 15,
    shadowColor: COLOR.BLACK,
    shadowOffset: {
      width: 2,
      height: 4,
    },
    shadowOpacity: 0.08,
    shadowRadius: 3.84,
    elevation: 5,
  },
});

export default RaceItem;
