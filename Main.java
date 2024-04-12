//Name: David DeCosta
//Date: 02/12/2024
//Assignment: Homework #2
/*Description:
        This program implements different encryption and decryption methods
 */
 
 public class Main{
     public static void main(String[] args) {

        //Question 1
        System.out.println("Question 1:");
        String encryptedText;
        String decryptedText;
        String originalText = "scooby dude";
        System.out.println("Original text: " + originalText);
        encryptedText = encryptionVig(originalText, "secret key");
        System.out.println("Encrypted text: " + encryptedText);
        decryptedText = decryptionVig(encryptedText, "secret key");
        System.out.println("Decrypted text: " + decryptedText);
         
        //Question 2
        System.out.println("\nQuestion 2:");
        String plainText = "something random";
        System.out.println("Normal text :" + plainText);
        String decrytedTextOne;
        String decryptedTextTwo;
        String cipherText;
        String key1 = "keyone";
        String key2 = "ahjqwcspuoplzjaw";
        cipherText = encryptionVig(plainText, key1);
        System.out.println("Cipher text one : " + cipherText);    // now decrypt this to get 2 reasonable plain text
        decrytedTextOne = decryptionVig(cipherText, key1);
        System.out.println("Decrypted Text one : " + decrytedTextOne);   //gives bak original text
        decryptedTextTwo = decryptionVig(cipherText, key2);
        System.out.println("Decrypted text two : " + decryptedTextTwo);  //gives back a text that is reasonable

        //Question 3
        System.out.println("\n Question 3: ");
        String normalText = "mr magoo";
        String key = "secretse";
        String cipherResult = vernamCipher(normalText, key);
        System.out.println("Normal text: " + normalText);
        System.out.println("Cipher text: " + cipherResult);
        String decryptTheCipherText = vernamCipher(cipherResult, key);
        System.out.println("Decrypted Cipher Text: " + decryptTheCipherText);

        //Question 5
        System.out.println("\n Question 5: ");
        String myString = "meetmeafterthetogaparty";
        String theNewString;
        System.out.println("Not encrypted: " + myString);
        theNewString = fenceCipher(myString, 2, 11);
        System.out.println("Cipher Text:   " + theNewString);
     }

     public static String encryptionVig(String msg, String key){
        StringBuilder encryptedMsgBuilder = new StringBuilder();
        char msgChar;
        char keyChar;
        key = matchKeyAndTextLength(key, msg);
    
        for(int i = 0; i < msg.length(); i++){
            msgChar = msg.charAt(i);
            keyChar = key.charAt(i);
    
            encryptedMsgBuilder.append(encryptCharacter(msgChar, keyChar));
        }
    
        return encryptedMsgBuilder.toString();
    }
    

    public static String decryptionVig(String ciphTxt, String key) {
        StringBuilder decryptedMsgBuilder = new StringBuilder();
        char cipherChar;
        char keyChar;
        key = matchKeyAndTextLength(key, ciphTxt); 
    
        for (int i = 0; i < ciphTxt.length(); i++) {  //get char at each position of both key and cipher text
            cipherChar = ciphTxt.charAt(i);
            keyChar = key.charAt(i);
    
            decryptedMsgBuilder.append(decryptCharacter(cipherChar, keyChar));
        }
    
        return decryptedMsgBuilder.toString();
    }
    

     public static String matchKeyAndTextLength(String key, String msg){
         String newKey = key;
         while (newKey.length() < msg.length()) {
            newKey += key;   //add until long enough
         }
         return newKey.substring(0, msg.length()); //exacty long as msg
    }

    public static char encryptCharacter(char charInMsg, char charInKey) {

      int msgIndex;
      int keyIndex;
      int encryptdIdx;

      //get index for char in the message
      if (charInMsg == ' ') {
          msgIndex = 26; // space will be  27th char
      } 
      else {
          msgIndex = charInMsg - 'a'; // calc index for letters a-z
      }
  
      //get index for char in Key  
      if (charInKey == ' ') {
          keyIndex = 26;
      } 
      else {
          keyIndex = charInKey - 'a';
      }
  
      encryptdIdx = (msgIndex + keyIndex) % 27;  // calc encryptd index
  
      if (encryptdIdx == 26) { //  put encrypted index back to a character
          return ' ';
      } 
      else {
          return (char) ('a' + encryptdIdx); // return the char
      }
   }

   public static char decryptCharacter(char charInCipher, char charInKey) {
      int cipherIdx;
      int keyIdx;
      int decryptIdx;

      if (charInCipher == ' ') {
          cipherIdx = 26;
      } else {
          cipherIdx = charInCipher - 'a';     //charInCipher j = 106, a=97,  106-97 = 9.  j=9
      }
  
      if (charInKey == ' ') {
          keyIdx = 26;
      } else {
          keyIdx = charInKey - 'a';         //charInKey s = 115, a=97,  115-97 = 18 .   s=18
      }
  
      decryptIdx = (cipherIdx - keyIdx + 27) % 27; // (9-18+27) % 27  =  18
  
      // put back to char
      if (decryptIdx == 26) {
          return ' ';
      } else {
          return (char) ('a' + decryptIdx);  //97 + 18  = 115 which is 's'
      }
  }

  public static String vernamCipher(String msg, String key){
    StringBuilder cipherString = new StringBuilder();

    char msgChar;
    char keyChar;
    int msgInt;
    int keyInt;

    for(int i = 0; i< msg.length(); i++){
        msgChar = msg.charAt(i);  //get m
        keyChar = key.charAt(i);  //get s

        msgInt = charToInt(msgChar);  // 12 m        01100
        keyInt = charToInt(keyChar);  //18  s        10010

        int xorMSGandKEY = (msgInt ^ keyInt) % 27;  //       11110    30 % 27= 3  = d
  //      int xorMSGandKEY = (msgInt ^ keyInt);    //gives back orignal text
        char theChar = intToChar(xorMSGandKEY);     // d
        cipherString.append(theChar);

    }

    return cipherString.toString();
  }

  public static int charToInt(char c){

    if(c == ' '){
        return 26;
    }
    else{
        return (int)(c - 'a');
    }
  }

  public static char intToChar(int x){

    if(x == 26){
        return ' ';
    }
    else{
        return (char)(x + 'a');
    }
  }

  public static String fenceCipher(String msg, int depth, int rounds) {

    int columns = (int) Math.ceil((double) msg.length() / depth); //to account for odd depths
    char[][] charArray = new char[depth][columns];
    int col = 0;
    int row = 0;
    String currentMsg = msg;  //need to account for rounds

    if (depth == 1){
        return msg;
    }

    for(int k = 0; k < rounds; k++){
        msg = currentMsg;    //will be different after each round
        StringBuilder theString = new StringBuilder(); //new one each round
        col = 0; //each round col goes back to 0

        //assign chars down the columns
        for (int i = 0; i < msg.length(); i++) {
            row = i % depth; // will go back to 0 once it hit the depth 2 % 2 = 0

            if (i > 0 && row == 0){ // dont go to next column right away
                col++; //go to next col
            }
            charArray[row][col] = msg.charAt(i);
    }
        //read acroos the rows
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < columns; j++) {
                    theString.append(charArray[i][j]);
            }
        }
        currentMsg = theString.toString();
    }

    return currentMsg;
}

}