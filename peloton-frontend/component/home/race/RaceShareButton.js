import React from "react";
import { Share, StyleSheet, TouchableOpacity } from "react-native";
import { EvilIcons } from "@expo/vector-icons";
import { useRecoilValue } from "recoil";
import { COLOR } from "../../../utils/constants";
import { raceInfoState } from "../../../state/race/RaceState";
import { raceShareLink } from "./RaceDeepLinkPage";

const RaceShareButton = () => {
  const raceInfo = useRecoilValue(raceInfoState);

  const onShare = async () => {
    await Share.share({
      url: raceShareLink(raceInfo.id),
    });
  };

  return (
    <TouchableOpacity style={styles.shareButton} onPress={onShare}>
      <EvilIcons name="share-apple" size={38} color={COLOR.BLACK} />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  shareButton: {
    width: 50,
    height: 50,
    borderRadius: 30,
    backgroundColor: "rgba(255,255,255,0.2)",
    alignItems: "center",
    justifyContent: "center",
  },
});

export default RaceShareButton;
