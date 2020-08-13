import React, { useCallback, useEffect, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { useRecoilState } from "recoil";
import { useFocusEffect } from "@react-navigation/core";

import {
  raceCertificationFixture,
  raceCertificationState,
} from "../../state/certification/RaceCertificationState";
import CertificationItem from "./CertificationItem";

const Certification = () => {
  const [certifications, setCertifications] = useRecoilState(
    raceCertificationState,
  );
  const [currentTime, setCurrentTime] = useState(new Date());

  useEffect(() => {
    setCertifications(raceCertificationFixture);
  }, []);

  useFocusEffect(
    useCallback(() => {
      const intervalId = setInterval(() => setCurrentTime(new Date()), 1000);
      return () => {
        clearInterval(intervalId);
      };
    }, [])
  );

  return (
    <View style={styles.container}>
      <FlatList
        data={certifications}
        renderItem={({ item }) => (
          <CertificationItem item={item} currentTime={currentTime} />
        )}
        keyExtractor={(item) => String.valueOf()(item.mission.id)}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Certification;
