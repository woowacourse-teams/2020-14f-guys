import React from "react";
import { Share, StyleSheet, TouchableOpacity } from "react-native";
import { EvilIcons } from "@expo/vector-icons";
import { COLOR, DEEP_LINK_BASE_URL } from "../../../utils/constants";
import { useRecoilValue } from "recoil/dist";
import { raceInfoState } from "../../../state/race/RaceState";

const RaceShareButton = () => {
  const raceInfo = useRecoilValue(raceInfoState);

  const onShare = async () => {
    await Share.share({
      url: `${DEEP_LINK_BASE_URL}races/${raceInfo.id}`,
    });
  };

  return (
    <TouchableOpacity style={styles.shareButton} onPress={onShare}>
      <EvilIcons name="share-apple" size={44} color={COLOR.BLACK} />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  shareButton: {
    padding: 5,
  },
});

export default RaceShareButton;
