import SwiftUI
import Mosaic

struct ComposeView: UIViewControllerRepresentable {
        
    @ObservedObject var viewModel: ContentViewModel  // Pass the view model as a parameter
    
    private let controllerHolder = MosaicCalController()
    
    init(viewModel: ContentViewModel) {
        self.viewModel = viewModel
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        viewModel.processIntent(intent: ContentIntent.loadData)
        let onSelectDate: (Kotlinx_datetimeLocalDate) -> Void = { localDate in
            viewModel.processIntent(intent: ContentIntent.selectDate(date: localDate))
        }
        
        return controllerHolder.makeUIViewController(onSelectDate: onSelectDate)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        controllerHolder.updateDaysOfWeek(daysOfWeek: viewModel.state.daysOfWeek)
        controllerHolder.updateMonths(months: viewModel.state.months)        
    }
}

struct ContentView: View {
    
    @ObservedObject private var viewModel: ContentViewModel = ContentViewModel()
    
    var body: some View {
        VStack {
            Button(action: {
                viewModel.processIntent(intent: ContentIntent.toggleMode)
               }) {
                   Text("Toggle mode") // Text displayed on the button
//                       .font(.headline) // Adjust font style
//                       .padding() // Add some padding around the text
//                       .background(Color.blue) // Set the background color
//                       .foregroundColor(.white) // Set the text color
//                       .cornerRadius(10) // Add rounded corners to the button
               }
            
            ComposeView(viewModel: viewModel).ignoresSafeArea(.keyboard) // Compose has own keyboard handler
        }
    }
}
