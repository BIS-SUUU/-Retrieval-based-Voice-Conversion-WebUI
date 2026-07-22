"""
Example script to convert a PyTorch model to TorchScript.
This is a template — adapt input shapes and model loading logic to your repository.
"""

import torch
import sys
from pathlib import Path


def convert(model_path: str, out_path: str):
    # Placeholder model loading. Replace with actual model class and loading.
    # from model import MyVCModel
    # model = MyVCModel()
    # checkpoint = torch.load(model_path)
    # model.load_state_dict(checkpoint['model'])

    # For demo, create a tiny dummy model
    class DummyModel(torch.nn.Module):
        def __init__(self):
            super().__init__()
            self.lin = torch.nn.Linear(10, 10)
        def forward(self, x):
            return self.lin(x)

    model = DummyModel()
    model.eval()

    # Example input tensor; adjust shape to your model's expected input
    example_input = torch.randn(1, 10)

    traced = torch.jit.trace(model, example_input)
    traced.save(out_path)
    print(f"Saved TorchScript model to {out_path}")


if __name__ == '__main__':
    if len(sys.argv) < 3:
        print("Usage: python convert_to_torchscript.py <model_path> <out_path>")
        sys.exit(1)
    convert(sys.argv[1], sys.argv[2])
