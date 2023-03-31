import React, {useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  TouchableOpacity,
  View,
  TextInput,
  NativeModules,
} from 'react-native';

const App = () => {
  const {TruthNFCModule} = NativeModules;

  const nfcRequiredData = {
    dob: new Date(),
    expiryDate: new Date(),
    documentNumber: 'AB1234567',
  };

  return (
    <SafeAreaView
      style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <TouchableOpacity
        style={styles.input}
        onPress={async () => {
          await TruthNFCModule.initialize();
          var data = await TruthNFCModule.scanPassport("AB1234567","820801","320712");
          console.log(data);
        }}>
        <Text>Press to SCAN</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
    width: 200,
  },
});

export default App;
