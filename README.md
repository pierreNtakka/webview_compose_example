# Webview in Jetpack Compose

Questo progetto è un esempio di utilizzo di **WebView** all'interno di un'app Android sviluppata con
**Jetpack Compose**.

L'obiettivo è mostrare come integrare una WebView in un layout Compose e come abilitare la
comunicazione tra il codice Kotlin e JavaScript tramite le **JavaScript interface**.

## Funzionalità principali

- Visualizzazione di una pagina HTML locale tramite WebView.
- Integrazione di JavaScript interface per permettere la comunicazione tra la WebView e il codice
  nativo Kotlin.
- Esempi di chiamate da JavaScript verso Kotlin e viceversa.

## Note di sicurezza

L'uso delle JavaScript interface può introdurre rischi di sicurezza (XSS). In questo esempio, la
WebView carica solo asset locali e le interfacce esposte sono limitate.

## Come provare

1. Clona il repository.
2. Apri il progetto in Android Studio.
3. Avvia l'app su un emulatore o dispositivo reale.

---

Questo esempio è pensato a scopo didattico per chi vuole imparare a integrare WebView e JavaScript
interface in Compose.