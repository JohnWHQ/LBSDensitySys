#include "pbtest.pb.h"
#include <iostream>  
#include <fstream> 
using namespace std;

void ListMsg(const gps_data & msg) { 
  cout << msg.id() << endl; 
 } 
  
int main(int argc, char* argv[]) { 
 
  gps_data msg1; 
  
  { 
    fstream input("./PbOutput", ios::in | ios::binary); 
    if (!msg1.ParseFromIstream(&input)) { 
      cerr << "Failed to parse." << endl; 
      return -1; 
    } 
  } 
  
  ListMsg(msg1); 
} 
